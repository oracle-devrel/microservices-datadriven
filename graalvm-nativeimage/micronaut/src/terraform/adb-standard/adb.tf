resource "oci_database_autonomous_database" "autonomous_database" {
  compartment_id              = var.compartment_ocid
  db_name                     = upper(replace(var.name, "-", ""))
  display_name                = format("%s%s", "adb-", var.name)
  db_workload                 = "OLTP"
  is_dedicated                = false
  is_free_tier                = false
  # db_version                  = "19c"
  cpu_core_count              = 1
  data_storage_size_in_tbs    = 1
  admin_password              = var.adb_admin_password
  # are_primary_whitelisted_ips_used = true
  is_mtls_connection_required = true
  # whitelisted_ips = ["192.168.0.234"]
  customer_contacts {
    email = var.adb_customer_contacts_email
  }
  
}
