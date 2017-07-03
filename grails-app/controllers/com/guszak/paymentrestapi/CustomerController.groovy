package com.guszak.paymentrestapi

import static org.springframework.http.HttpStatus.*
import grails.validation.ValidationException
import grails.rest.RestfulController

class List {
	String object = 'list'
	Boolean hasMore
	Integer totalCount
	Integer limit
	Integer offset
}

class CustomerController extends RestfulController{

	def customerService
	
	static responseFormats = ['json']
	CustomerController() {
		super(Customer)
	}

	/**
     * Lists all customers up to the given maximum
     *
     * @param max The maximum
     * @return A list of customers
     */
     def index(Integer limit) {
     	params.max = Math.min(limit ?: 10, 100)
     	respond Customer.noDeleted.search(params.searchString).list(params), model: [customerInstanceCount: Customer.count()]
     }

    /**
    * Updates a customer for the given id
    * @param id
    */
    def delete() {
    	def customer = Customer.get(params.id)
    	if (customer == null) {
    		notFound()
    		return
    	}

    	def numberOfPayments = Payment.noDeleted.count()
    	if( numberOfPayments > 0 ){
    		notFound()
    		return
    	}

    	customer.deleted = true
    	customer.save flush:true

    	request.withFormat {
    		form multipartForm {
    			flash.message = message(code: 'default.deleted.message', args: [message(code: 'Customer.label', default: 'Customer'), customer.id])
    			redirect action:"index", method:"GET"
    		}
    		'*' { render status: NO_CONTENT }
    	}
    }
}