package com.guszak.paymentrestapi

class Payment {

	Integer customer
    BigDecimal value
    String dueDate
    String billingType
    String status = 'PENDING'
    String description
    Boolean deleted = false

	static constraints = {
		customer blank: false
        value blank: false, min: 20.0, scale: 2
        dueDate blank: false//, min: new Date()
        billingType blank: false, inList: ["BOLETO","CREDIT_CARD"]
        status blank: false, inList: ["PENDING","RECEIVED","OVERDUE"]
        description nullable: true
	}

    static namedQueries = {
       noDeleted {
           eq 'deleted', false
       }
   }
}