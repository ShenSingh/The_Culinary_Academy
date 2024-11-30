package com.zenveus.the_culinary_academy.bo.custom;

import com.zenveus.the_culinary_academy.bo.SuperBO;

import java.util.List;

public interface ChartBO extends SuperBO {
    int getProgramCount();

    int getStudentCount();

    int getCoordinatorCount();

    List<Object[]> getStudentCourseCount();

    List<Object[]> getMonthlyTotalPayments();

    List<Object[]> getProgramsCountByDuration();
}
