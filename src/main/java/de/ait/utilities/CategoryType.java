package de.ait.utilities;

public enum CategoryType {
    // income
    INCOME_TICKET_SALES,      // Продаж квитків
    INCOME_SPONSORSHIPS,      // Спонсорські внески
    INCOME_CONTRACT_FEES,     // Гонорари за контракти
    INCOME_MERCHANDISE,       // Дохід від мерчандайзу
    INCOME_STREAMING,         // Дохід від стрімінгів
    INCOME_OTHER,             // Інші доходи

    // expense
    EXPENSE_ARTIST_FEES,      // Оплата артистів
    EXPENSE_VENUE_RENTAL,     // Оренда майданчиків
    EXPENSE_MARKETING,        // Маркетинг та реклама
    EXPENSE_STAFF,            // Витрати на персонал
    EXPENSE_TECHNICAL,        // Технічне забезпечення
    EXPENSE_LOGISTICS,        // Логістика (транспорт)
    INCOME_SALARY, EXPENSE_FOOD, INCOME_BUSINESS, EXPENSE_RENT, EXPENSE_SPORT, EXPENSE_OTHER             // Інші витрати
}