package com.me.mseotsanyana.mande.UTIL;

import com.me.mseotsanyana.mande.BLL.model.monitor.cIndicatorMilestoneModel;
import com.me.mseotsanyana.mande.BLL.model.monitor.cIndicatorModel;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.me.mseotsanyana.mande.UTIL.cConstant.ANNUALLY_ID;
import static com.me.mseotsanyana.mande.UTIL.cConstant.DAILY_ID;
import static com.me.mseotsanyana.mande.UTIL.cConstant.FORT_NIGHTLY_ID;
import static com.me.mseotsanyana.mande.UTIL.cConstant.MONTHLY_ID;
import static com.me.mseotsanyana.mande.UTIL.cConstant.QUARTERLY_ID;
import static com.me.mseotsanyana.mande.UTIL.cConstant.WEEKLY_ID;

public class cMilestoneUtils {
    public static ArrayList<cIndicatorMilestoneModel> generateDailySequence(
            LocalDate startDate, LocalDate endDate, cIndicatorModel indicatorModel) {

        ArrayList<cIndicatorMilestoneModel> milestoneModels = new ArrayList<>();
        cIndicatorMilestoneModel milestoneModel = null;

        // iterate thorough days
        //int index = 0;
        LocalDate currentDailyStart = startDate;
        while (!currentDailyStart.isAfter(endDate)) {

            milestoneModel = new cIndicatorMilestoneModel();
            
            milestoneModel.setIndicatorModel(indicatorModel);

            milestoneModel.setName("Daily");
            milestoneModel.setDescription("Day "+currentDailyStart.getDayOfYear()+
                    " of "+currentDailyStart.getYear());

            // assign start and end dates
            milestoneModel.setStartDate(convertLocalDate2Date(currentDailyStart));
            milestoneModel.setEndDate(convertLocalDate2Date(currentDailyStart.plusDays(1)));

            milestoneModels.add(milestoneModel);

            currentDailyStart = currentDailyStart.plusDays(1);

            //index = index + 1;
        }

        //indicator

        /*for (int i = 0; i < milestoneModels.size(); i++){
            milestoneModels.get(i).setBaselineValue(targetModel.getBaseline()/index);
            milestoneModels.get(i).setTargetValue(targetModel.getTarget());
        }*/

        return milestoneModels;
    }

    public static ArrayList<cIndicatorMilestoneModel> generateWeeklySequence(LocalDate startDate,
                                                                       LocalDate endDate) {
        ArrayList<cIndicatorMilestoneModel> milestoneModels = new ArrayList<>();
        cIndicatorMilestoneModel milestoneModel = null;

        // iterate thorough quarters
        LocalDate currentWeeklyStart = startDate;
        while (!currentWeeklyStart.isAfter(endDate)) {

            milestoneModel = new cIndicatorMilestoneModel();

            milestoneModel.setName("Weekly");
            milestoneModel.setDescription("Day of Week "+currentWeeklyStart.getDayOfWeek()+
                    " of "+currentWeeklyStart.getYear());

            // assign start and end dates
            milestoneModel.setStartDate(convertLocalDate2Date(currentWeeklyStart));
            milestoneModel.setEndDate(convertLocalDate2Date(currentWeeklyStart.plusWeeks(1)));

            milestoneModels.add(milestoneModel);

            currentWeeklyStart = currentWeeklyStart.plusWeeks(1);
        }

        return milestoneModels;
    }

    public static ArrayList<cIndicatorMilestoneModel> generateFortNightlySequence(LocalDate startDate,
                                                                    LocalDate endDate) {
        ArrayList<cIndicatorMilestoneModel> milestoneModels = new ArrayList<>();
        cIndicatorMilestoneModel milestoneModel = null;

        // iterate thorough quarters
        LocalDate currentFortNightlyStart = startDate;
        while (!currentFortNightlyStart.isAfter(endDate)) {

            milestoneModel = new cIndicatorMilestoneModel();

            milestoneModel.setName("Fortnightly");
            milestoneModel.setDescription("Day of Week "+currentFortNightlyStart.getDayOfWeek()+
                    " of "+currentFortNightlyStart.getYear());

            // assign start and end dates
            milestoneModel.setStartDate(convertLocalDate2Date(currentFortNightlyStart));
            milestoneModel.setEndDate(convertLocalDate2Date(currentFortNightlyStart.plusWeeks(1)));

            milestoneModels.add(milestoneModel);

            currentFortNightlyStart = currentFortNightlyStart.plusWeeks(2);
        }

        return milestoneModels;
    }

    public static ArrayList<cIndicatorMilestoneModel> generateMonthlySequence(LocalDate startDate,
                                                                         LocalDate endDate) {
        ArrayList<cIndicatorMilestoneModel> milestoneModels = new ArrayList<>();
        cIndicatorMilestoneModel milestoneModel = null;

        // iterate thorough quarters
        LocalDate currentMonthlyStart = startDate;
        while (!currentMonthlyStart.isAfter(endDate)) {

            milestoneModel = new cIndicatorMilestoneModel();

            milestoneModel.setName("Monthly");
            milestoneModel.setDescription("Month "+currentMonthlyStart.getMonth().name()+
                    " of "+currentMonthlyStart.getYear());

            // assign start and end dates
            milestoneModel.setStartDate(convertLocalDate2Date(currentMonthlyStart));
            milestoneModel.setEndDate(convertLocalDate2Date(currentMonthlyStart.plusWeeks(1)));

            milestoneModels.add(milestoneModel);

            currentMonthlyStart = currentMonthlyStart.plusWeeks(2);
        }

        return milestoneModels;
    }

    public static ArrayList<cIndicatorMilestoneModel> generateQuarterlySequence(LocalDate startDate,
                                                                LocalDate endDate) {
        // first truncate startDate to first day of quarter
        int startMonth = startDate.getMonthValue();
        startMonth -= (startMonth - 1) % 3;
        startDate = startDate.withMonth(startMonth).withDayOfMonth(1);

        DateTimeFormatter nameFormatter
                = DateTimeFormatter.ofPattern("QQQ", Locale.ENGLISH);
        DateTimeFormatter descriptionFormatter
                = DateTimeFormatter.ofPattern("uuuuQQQ", Locale.ENGLISH);

        ArrayList<cIndicatorMilestoneModel> milestoneModels = new ArrayList<>();
        cIndicatorMilestoneModel milestoneModel = null;

        // iterate thorough quarters
        LocalDate currentQuarterStart = startDate;
        while (!currentQuarterStart.isAfter(endDate)) {

            milestoneModel = new cIndicatorMilestoneModel();

            milestoneModel.setName("Quarterly");
            if (currentQuarterStart.format(nameFormatter).equals("Q1")) {
                milestoneModel.setDescription("First Quarter of "+currentQuarterStart.getYear());
            }
            if (currentQuarterStart.format(nameFormatter).equals("Q2")) {
                milestoneModel.setDescription("Second Quarter of "+currentQuarterStart.getYear());
            }
            if (currentQuarterStart.format(nameFormatter).equals("Q3")) {
                milestoneModel.setDescription("Third Quarter of "+currentQuarterStart.getYear());
            }
            if (currentQuarterStart.format(nameFormatter).equals("Q4")) {
                milestoneModel.setDescription("Fourth Quarter of "+currentQuarterStart.getYear());
            }

            // assign start and end dates
            milestoneModel.setStartDate(convertLocalDate2Date(currentQuarterStart));
            milestoneModel.setEndDate(convertLocalDate2Date(currentQuarterStart.plusMonths(3)));

            milestoneModels.add(milestoneModel);

            currentQuarterStart = currentQuarterStart.plusMonths(3);
        }

        return milestoneModels;
    }

    public static ArrayList<cIndicatorMilestoneModel> generateAnnuallySequence(LocalDate startDate,
                                                                     LocalDate endDate) {
        ArrayList<cIndicatorMilestoneModel> milestoneModels = new ArrayList<>();
        cIndicatorMilestoneModel milestoneModel = null;

        // iterate thorough quarters
        LocalDate currentAnnuallyStart = startDate;
        while (!currentAnnuallyStart.isAfter(endDate)) {

            milestoneModel = new cIndicatorMilestoneModel();

            milestoneModel.setName("Annually");
            milestoneModel.setDescription("Year "+currentAnnuallyStart.getYear());

            // assign start and end dates
            milestoneModel.setStartDate(convertLocalDate2Date(currentAnnuallyStart));
            milestoneModel.setEndDate(convertLocalDate2Date(currentAnnuallyStart.plusYears(1)));

            milestoneModels.add(milestoneModel);

            currentAnnuallyStart = currentAnnuallyStart.plusYears(1);
        }

        return milestoneModels;
    }


    public static LocalDate convertDate2LocalDate(Date date) {
        return LocalDate.from(Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()));
    }

    public static Date convertLocalDate2Date(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }


    public static ArrayList<cIndicatorMilestoneModel> getMilestones(cIndicatorModel indicatorModel,
                                                          Date startingDate, Date endingDate){
        ArrayList<cIndicatorMilestoneModel> milestoneModels = new ArrayList<>();

        long frequencyID = indicatorModel.getFrequencyModel().getFrequencyID();

        if (frequencyID == DAILY_ID) {
            LocalDate startDate = cMilestoneUtils.convertDate2LocalDate(startingDate);
            LocalDate endDate = cMilestoneUtils.convertDate2LocalDate(endingDate);
            milestoneModels = cMilestoneUtils.generateDailySequence(startDate, endDate, indicatorModel);
        }

        if (frequencyID == WEEKLY_ID) {
            LocalDate startDate = cMilestoneUtils.convertDate2LocalDate(startingDate);
            LocalDate endDate = cMilestoneUtils.convertDate2LocalDate(endingDate);
            milestoneModels = cMilestoneUtils.generateWeeklySequence(startDate, endDate);
        }

        if (frequencyID == FORT_NIGHTLY_ID) {
            LocalDate startDate = cMilestoneUtils.convertDate2LocalDate(startingDate);
            LocalDate endDate = cMilestoneUtils.convertDate2LocalDate(endingDate);
            milestoneModels = cMilestoneUtils.generateFortNightlySequence(startDate, endDate);
        }

        if (frequencyID == MONTHLY_ID) {
            LocalDate startDate = cMilestoneUtils.convertDate2LocalDate(startingDate);
            LocalDate endDate = cMilestoneUtils.convertDate2LocalDate(endingDate);
            milestoneModels = cMilestoneUtils.generateMonthlySequence(startDate, endDate);
        }

        if (frequencyID == QUARTERLY_ID) {
            LocalDate startDate = cMilestoneUtils.convertDate2LocalDate(startingDate);
            LocalDate endDate = cMilestoneUtils.convertDate2LocalDate(endingDate);
            milestoneModels = cMilestoneUtils.generateQuarterlySequence(startDate, endDate);
        }

        if (frequencyID == ANNUALLY_ID) {
            LocalDate startDate = cMilestoneUtils.convertDate2LocalDate(startingDate);
            LocalDate endDate = cMilestoneUtils.convertDate2LocalDate(endingDate);
            milestoneModels = cMilestoneUtils.generateAnnuallySequence(startDate, endDate);
        }

        return milestoneModels;
    }
}
