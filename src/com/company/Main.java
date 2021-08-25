package com.company;

import com.company.dto.Person;
import com.company.services.PersonClassificationService;

import java.util.List;

public class Main {

    private static final List<String> pollResults = List.of(
            "служанка Аня",
            "управляющий Семен Семеныч: крестьянин Федя, доярка Нюра",
            "дворянин Кузькин: управляющий Семен Семеныч, жена Кузькина, экономка Лидия Федоровна",
            "экономка Лидия Федоровна: дворник Гена, служанка Аня",
            "доярка Нюра",
            "кот Василий: человеческая особь Катя",
            "дворник Гена: посыльный Тошка",
            "киллер Гена",
            "зажиточный холоп: крестьянка Таня",
            "секретарь короля: зажиточный холоп, шпион Т",
            "шпион Т: кучер Д",
            "посыльный Тошка: кот Василий",
            "аристократ Клаус",
            "просветленный Антон"
    );

    public static void main(String[] args) {
        UnluckyVassal unluckyVassal = new UnluckyVassal();

        unluckyVassal.printReportForKing(pollResults);
    }

    static class UnluckyVassal {
        public void printReportForKing(List<String> pollResults) {

            var personClassificationService = new PersonClassificationService();
            personClassificationService.classifyPersons(pollResults);

            var resultedList = personClassificationService.findPersonsByIerarhieLevel(0);
            resultedList.forEach(Person::print);
        }
    }


}
