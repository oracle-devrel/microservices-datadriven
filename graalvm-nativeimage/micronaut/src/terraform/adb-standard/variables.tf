# general
variable "compartment_ocid" {
  type        = string
  default     = "<YOUR_COMPARTMENT_OCID>"
  description = "OCID for your compartment"
}

# naming
variable "name" {
  type        = string
  default     = "MicronautDemo"
  description = "MicronautDemo"
}

# adb
variable "adb_admin_password" {
  type        = string
  default     = "<YOUR_DB_ADMIN_PW>"
  description = "ADB pw"
}
variable "adb_customer_contacts_email" {
  type        = string
  default     = "<YOUR_EMAIL_ADDRESS>"
  description = "email address"
}
