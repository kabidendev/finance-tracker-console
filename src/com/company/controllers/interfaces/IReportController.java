package com.company.controllers.interfaces;

import com.company.models.dto.TransactionFullView;
import java.util.List;
import java.util.Map;

public interface IReportController {

    List<TransactionFullView> getLastNTransactions(int userId, int n);
    Map<String, Double> getTopExpenseCategories(int userId);
}
