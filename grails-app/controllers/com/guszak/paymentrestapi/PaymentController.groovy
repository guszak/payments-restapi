package com.guszak.paymentrestapi

import static org.springframework.http.HttpStatus.*
import grails.rest.RestfulController

class PaymentController extends RestfulController{

	static responseFormats = ['json']
	PaymentController() {
		super(Payment)
	}

	/**
     * Lists all customers up to the given maximum
     *
     * @param max The maximum
     * @return A list of customers
     */
     def index(Integer limit) {
     	params.max = Math.min(limit ?: 10, 100)
     	respond Payment.noDeleted.list(params), model: [paymentInstanceCount: Payment.count()]
     }


	/**
    * Updates a payment for the given id
    * @param id
    */
    def delete() {
    	def payment = Payment.get(params.id)
    	if (payment == null) {
    		notFound()
    		return
    	}

    	def numberOfPayments = Payment.noDeleted.count()
 		if( payment.status == 'RECEIVED' ){
 			notFound()
    		return
 		}

    	payment.deleted = true
    	payment.save flush:true

    	request.withFormat {
    		form multipartForm {
    			flash.message = message(code: 'default.deleted.message', args: [message(code: 'Payment.label', default: 'Payment'), payment.id])
    			redirect action:"index", method:"GET"
    		}
    		'*' { render status: NO_CONTENT }
    	}
    }

}
