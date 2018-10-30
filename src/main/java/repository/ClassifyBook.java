package repository;

import entity.CompareGroup;
import entity.RawBook;
import entity.SuggestGroup;
import repository.generic.impl.GenericRepositoryImpl;
import repository.impl.CompareGroupRepositoryImpl;
import repository.impl.RawBookRepositoryImpl;
import repository.impl.SuggestGroupRepositoryImpl;
import utils.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ClassifyBook {

    public static void main(String[] args) {
        CompareGroupRepository compareGroupRepository = new CompareGroupRepositoryImpl(GenericRepositoryImpl.getFactory());
        SuggestGroupRepository suggestGroupRepository = new SuggestGroupRepositoryImpl(GenericRepositoryImpl.getFactory());
        RawBookRepository bookRepository = new RawBookRepositoryImpl(GenericRepositoryImpl.getFactory());

        List<RawBook> books = bookRepository.searchAll();

        List<CompareGroup> compareGroups = compareGroupRepository.searchAll();
        List<SuggestGroup> suggestGroups = suggestGroupRepository.searchAll();

        for (RawBook book : books) {
            if (book.getCompareGroupId() == null) {
                boolean pass = false;

                CompareGroup candidateGroup = null;
                double candidateSimilarRate = 0;

                for (CompareGroup compareGroup : compareGroups) {
                    if (compareGroup.checkMemberSource(book.getId())) break; // Two books of same source => then it must be two group!!

                    String groupTitle = compareGroup.getTitle();
                    String bookTitle = book.getTitle();

                    double similarRate = StringUtils.calculateLCS(bookTitle, groupTitle, true);
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
        }

        for (CompareGroup compareGroup : compareGroups) {
            if (compareGroup.getSuggestGroupId() == null) {
                boolean pass = false;

                for (SuggestGroup suggestGroup : suggestGroups) {

                    String guestTitle = compareGroup.getTitle();
                    String hostTitle = suggestGroup.getTitle();

                    double similarRate = StringUtils.calculateLCS(hostTitle, guestTitle, true);
                    if (similarRate >= 0.8) {
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

        System.out.println();

        List<CompareGroup> rs = compareGroups.stream().filter(group -> group.countMembers() > 1).collect(Collectors.toList());

        for (CompareGroup group : rs) {
            System.out.println(group);
            List<RawBook> group1 = books.stream().filter(rawBook -> rawBook.getCompareGroupId().equals(group.getId())).collect(Collectors.toList());

            for (RawBook rawBook : group1) {
                System.out.println(rawBook);
            }
            System.out.println();
        }

        System.out.println(books.size());
        System.out.println(compareGroups.size());
        System.out.println(rs.size());

        List<SuggestGroup> rs2 = suggestGroups.stream().filter(group -> group.countMembers() > 1).collect(Collectors.toList());

        for (SuggestGroup group : rs2) {
            System.out.println(group);

            List<CompareGroup> group1 = compareGroups.stream().filter(compareGroup -> compareGroup.getSuggestGroupId().equals(group.getId())).collect(Collectors.toList());

            for (CompareGroup rawBook : group1) {
                System.out.println(rawBook);
            }
            System.out.println();
        }

        System.out.println(compareGroups.size());
        System.out.println(suggestGroups.size());
        System.out.println(rs2.size());


//        for (Group group : groups) {
//            groupRepository.insertOrReplace(group);
//        }
//
//        for (RawBook rawBook : books) {
//            bookRepository.insertOrReplace(rawBook);
//        }
    }
}
