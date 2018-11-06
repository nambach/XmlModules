package repository;

import entity.CompareGroup;
import entity.RawBook;
import entity.SuggestGroup;
import repository.generic.impl.GenericRepositoryImpl;
import repository.impl.CompareGroupRepositoryImpl;
import repository.impl.RawBookRepositoryImpl;
import repository.impl.SuggestGroupRepositoryImpl;
import utils.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClassifyBook {

    public static void main(String[] args) {
        CompareGroupRepository compareGroupRepository = new CompareGroupRepositoryImpl(GenericRepositoryImpl.getFactory());
        SuggestGroupRepository suggestGroupRepository = new SuggestGroupRepositoryImpl(GenericRepositoryImpl.getFactory());
        RawBookRepository bookRepository = new RawBookRepositoryImpl(GenericRepositoryImpl.getFactory());

        List<RawBook> books = bookRepository.searchExactColumn(Collections.singletonList("true"), "status");

        List<CompareGroup> compareGroups = new ArrayList<>();
        List<SuggestGroup> suggestGroups = new ArrayList<>();

        compareGroupRepository.clearData();
        suggestGroupRepository.clearData();

        long start = System.currentTimeMillis();

        for (RawBook book : books) {
                boolean pass = false;

                CompareGroup candidateGroup = null;
                double candidateSimilarRate = 0;

                for (CompareGroup compareGroup : compareGroups) {
                    if (compareGroup.checkMemberSource(book.getId())) continue; // Two books with same source can not be in a same group

                    String groupTitle = compareGroup.getTitle();
                    String bookTitle = book.getTitle();

                    // Remove authors name in title (if any)
                    if (compareGroup.getAuthors() != null && !compareGroup.getAuthors().equals("")) {
                        bookTitle = bookTitle.replace(compareGroup.getAuthors(), "");
                    }

                    double similarRate = StringUtils.calculateLCSubstring(bookTitle, groupTitle).calculateIdentity();
                    if (similarRate >= 0.8 && similarRate > candidateSimilarRate) {
                        candidateGroup = compareGroup;
                        candidateSimilarRate = similarRate;
                        pass = true;
                    }
                }

                if (pass) {
                    candidateGroup.addMember(book.getId());
                    candidateGroup.updateBookInfo(book);

                    book.setCompareGroupId(candidateGroup.getId());
                }

                if (!pass) {
                    CompareGroup newGroup = new CompareGroup();
                    newGroup.setId(book.getId());
                    newGroup.setCoreMember(book.getId());
                    newGroup.addMember(book.getId());
                    newGroup.importCoreMember(book);

                    book.setCompareGroupId(newGroup.getId());

                    compareGroups.add(newGroup);
                }
        }

        System.out.println("Compares: " + compareGroups.size());

//        compareGroupRepository.insertBatch(compareGroups);

        for (CompareGroup compareGroup : compareGroups) {
            if (compareGroup.getSuggestGroupId() == null) {
                boolean pass = false;

                for (SuggestGroup suggestGroup : suggestGroups) {

                    String guestTitle = compareGroup.getTitle();
                    String hostTitle = suggestGroup.getTitle();

                    double lcsSeqRate = StringUtils.calculateLCSubsequence(hostTitle, guestTitle).calculateSimilarity(6);
                    double lcsStrRate = StringUtils.calculateLCSubstring(hostTitle, guestTitle).calculateSimilarity();
                    if (lcsSeqRate >= 0.8 || lcsStrRate >= 0.7) {
                        compareGroup.setSuggestGroupId(suggestGroup.getId());
                        suggestGroup.addMember(compareGroup.getId());

                        pass = true;
                        break;
                    }
                }

                if (!pass) {
                    SuggestGroup newSuggestGroup = new SuggestGroup();
                    newSuggestGroup.setId(compareGroup.getId());
                    newSuggestGroup.setCoreMember(compareGroup.getCoreMember());
                    newSuggestGroup.setTitle(compareGroup.getTitle());
                    newSuggestGroup.addMember(compareGroup.getCoreMember());

                    compareGroup.setSuggestGroupId(newSuggestGroup.getId());

                    suggestGroups.add(newSuggestGroup);
                }
            }
        }

        suggestGroupRepository.insertBatch(suggestGroups);
        compareGroupRepository.insertBatch(compareGroups);

//        System.out.println();
//
//        List<CompareGroup> rs = compareGroups.stream().filter(group -> group.countMembers() > 1).collect(Collectors.toList());
//
//        for (CompareGroup group : rs) {
//            System.out.println(group);
//            List<RawBook> group1 = books.stream().filter(rawBook -> rawBook.getCompareGroupId().equals(group.getId())).collect(Collectors.toList());
//
//            for (RawBook rawBook : group1) {
//                System.out.println(rawBook);
//            }
//            System.out.println();
//        }

        System.out.println(books.size());
//        System.out.println(compareGroups.size());
//        System.out.println(rs.size());

//        List<SuggestGroup> rs2 = suggestGroups.stream().filter(group -> group.countMembers() > 1).collect(Collectors.toList());

//        for (SuggestGroup group : rs2) {
//            System.out.println(group);
//
//            List<CompareGroup> group1 = compareGroups.stream().filter(compareGroup -> compareGroup.getSuggestGroupId().equals(group.getId())).collect(Collectors.toList());
//
//            for (CompareGroup rawBook : group1) {
//                System.out.println(rawBook);
//            }
//            System.out.println();
//        }

        System.out.println(compareGroups.size());
        System.out.println(suggestGroups.size());
//        System.out.println(rs2.size());

        System.out.println(System.currentTimeMillis() - start);
    }
}
