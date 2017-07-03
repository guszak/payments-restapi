package com.guszak.paymentrestapi

import grails.validation.ValidationException
import grails.transaction.Transactional

@Transactional
class CustomerService {

	def delete(id) {
		def customer = Customer.get(id)
		def numberOfPayments = Payment.noDeleted.count()
		if( numberOfPayments > 0 ){
			return error = true
		}else{
			customer.deleted = true
			customer.save flush:true
		}        
	}
}
