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

      // Oracle Autonomous Database (ADB ATP instance)
      ods.setURL(
          "jdbc:oracle:thin:@micronautdemo3_tpurgent?TNS_ADMIN=<PATH_TO_YOUR_UNCOMPRESSED_WALLET/Wallet_MICRONAUTDEMO3");
      ods.setUser("<YOUR_ADB_ATP_USERNAME");
      ods.setPassword("<YOUR_ADB_ATP_PASSWORD");

      // Oracle Database (LOCAL)
      // jdbc:oracle:thin@[hostname]:[port]/[DB service/name]
      // ods.setURL("jdbc:oracle:thin:@localhost:1521/FREEPDB1");
      // ods.setUser("<YOUR_LOCAL_DB_USERNAME>");
      // ods.setPassword("<YOUR_LOCAL_DB_PASSWORD>");

      Connection conn = ods.getConnection();
      PreparedStatement stmt = conn
          .prepareStatement("SELECT 'Hello World!' FROM dual");
      ResultSet rslt = stmt.executeQuery();
      while (rslt.next()) {
        System.out.println(rslt.getString(1));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
