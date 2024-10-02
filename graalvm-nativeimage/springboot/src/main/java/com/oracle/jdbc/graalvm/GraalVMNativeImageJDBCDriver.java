package com.oracle.jdbc.graalvm;

/*
Copyright (c) 2021, 2023, Oracle and/or its affiliates.
This software is dual-licensed to you under the Universal Permissive License
(UPL) 1.0 as shown at https://oss.oracle.com/licenses/upl or Apache License
2.0 as shown at http://www.apache.org/licenses/LICENSE-2.0. You may choose
either license.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
   https://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.pool.OracleDataSource;

public class GraalVMNativeImageJDBCDriver {
	
	public static void main(String[] args) {
		OracleDataSource ods;
		try {
			ods = new OracleDataSource();
			//notice the servicename suffix appended, which can be _high, _low, ...
//			ods.setURL("jdbc:oracle:thin:@${ATP Name}_high?TNS_ADMIN=/home/${MY_HOME_DIR}/myatpwallet");
			ods.setURL("jdbc:oracle:thin:@ATP110345_high?TNS_ADMIN=/home/LL110345_U/myatpwallet");
			ods.setUser("ADMIN");
//			ods.setPassword("[ATP Admin Password]");
			ods.setPassword("##03pRivvzR2q4jv");

			Connection conn = ods.getConnection();
			System.out.println("Oracle Database Connection:" + conn);
			PreparedStatement stmt = conn.prepareStatement("SELECT 'Hello World!' FROM dual");
			ResultSet rslt = stmt.executeQuery();
			while (rslt.next()) {
				System.out.println(rslt.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
