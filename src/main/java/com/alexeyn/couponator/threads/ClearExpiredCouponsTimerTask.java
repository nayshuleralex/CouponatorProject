package com.alexeyn.couponator.threads;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.TimerTask;

import com.alexeyn.couponator.enums.ErrorTypes;
import com.alexeyn.couponator.exceptions.ApplicationException;
import com.alexeyn.couponator.utils.JdbcUtils;

public class ClearExpiredCouponsTimerTask extends TimerTask {

	@Override
	public void run() {
		try {
			clearExpiredCoupons();
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void clearExpiredCoupons() throws ApplicationException {
		Connection connection= null;
		PreparedStatement preparedStatement = null;
		try {
			connection = JdbcUtils.getConnection();
			
			String sqlStatement = null;
			sqlStatement = "DELETE Coupons, Purchases "
					+ "FROM Coupons INNER JOIN Purchases "
					+ "ON Coupons.id = Purchases.couponId "
					+ "WHERE Coupons.endDate = CURDATE()";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, "Clear expired coupon Failed");
		}
		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

}
