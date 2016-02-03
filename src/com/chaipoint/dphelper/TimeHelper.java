package com.chaipoint.dphelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.chaipoint.dppojos.TimeSliceDistanceTravel;
import com.chaipoint.dpselection.DPStatus;
import com.chaipoint.dpselection.TimeCalculator;

public class TimeHelper {
	private static final Logger logger = Logger.getLogger(TimeHelper.class);

	// add mitune to a Date type
	public Date addMinute(Date date, long minute) {

		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, (int) minute);
		String newTime = format.format(cal.getTime());

		try {
			date = format.parse(newTime);
		} catch (ParseException e) {
			logger.error("exception while parsing string to date" + e.getStackTrace());

		}
		return date;
	}

	// min return frem DPs
	public TimeCalculator TimeSetter(ArrayList<DPStatus> dpstatus, String storeId) {

		ArrayList<TimeCalculator> timeCalculator = new ArrayList<TimeCalculator>();

		for (DPStatus status : dpstatus) {
			TimeCalculator calculator = new TimeCalculator();
			calculator.setDPId(status.getDPname());
			calculator.setTimeSlice(getTimeSlice(status.getLastDispatchTime()));
			calculator.setTimeForUnitKm(getUnitKmTime(storeId, calculator.getTimeSlice()));
			calculator.setExpectedReturnTIme(expectedReturnTIme(calculator.getTimeForUnitKm(),
					status.getLastTotalDistance(), status.getLastDispatchTime()));
			timeCalculator.add(calculator);

		}

		Collections.sort(timeCalculator, new Comparator<TimeCalculator>() {
			public int compare(TimeCalculator s1, TimeCalculator s2) {
				int result = 0; // assume equal
				if ((s1 == null && s2 != null)
						|| (s1.getExpectedReturnTIme().getTime() >= s2.getExpectedReturnTIme().getTime())) {
					result = 1;
				}
				if ((s1 != null && s2 == null)
						|| (s1.getExpectedReturnTIme().getTime() < s2.getExpectedReturnTIme().getTime())) {
					result = -1;
				}
				return result;
			}
		});

		return timeCalculator.get(0);
	}

	private Date expectedReturnTIme(int timeForUnitKm, int lastTotalDistance, Date lastDispatchTime) {

		lastDispatchTime = new Date();
		Date date = addMinute(lastDispatchTime, (lastTotalDistance * timeForUnitKm));
		return date;
	}

	// unit time for a particular store in particular time
	public int getUnitKmTime(String storeId, String timeSlice) {

		int unitTime = 0;

		if (timeSlice.equals("8-11"))
			timeSlice = "firstSlice";
		else if (timeSlice.equals("11-3"))
			timeSlice = "secondSlice";
		else if (timeSlice.equals("3-5"))
			timeSlice = "thirdSlice";
		else if (timeSlice.equals("5-7"))
			timeSlice = "fourthSlice";
		else if (timeSlice.equals("7-10"))
			timeSlice = "fifthSlice";

		HibernateTemplate hibernateTemplate = new HibernateTemplate();
		Criteria criteria = new HibernateTemplate().getSession().createCriteria(TimeSliceDistanceTravel.class);
		criteria.add(Restrictions.eq("storeId", storeId));
		criteria.setProjection(Projections.property(timeSlice));
		ArrayList<Integer> list = (ArrayList<Integer>) hibernateTemplate.get(criteria);
		unitTime = list.get(0);
		return unitTime;
	}

	// give time slice foe each order
	public String getTimeSlice(Date lastDispatchTime) {

		String slice = null;
		lastDispatchTime = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(lastDispatchTime);
		int hours = calendar.get(Calendar.HOUR_OF_DAY);

		if (hours >= 8 && hours < 11)
			slice = "8-11";
		else if (hours >= 11 && hours < 15)
			slice = "11-3";
		else if (hours >= 15 && hours < 17)
			slice = "3-5";
		else if (hours >= 17 && hours < 19)
			slice = "5-7";
		else if (hours >= 19 && hours < 22)
			slice = "7-10";
		return slice;

	}

}
