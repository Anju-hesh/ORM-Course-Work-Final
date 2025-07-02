package lk.ijse.gdse72.ormfinalcoursework.bo.custom;

import lk.ijse.gdse72.ormfinalcoursework.bo.SuperBO;

import java.util.Map;

public interface OwnerDashboardBo extends SuperBO {
    Map<String, Integer> getOverviewData() throws Exception;
}
