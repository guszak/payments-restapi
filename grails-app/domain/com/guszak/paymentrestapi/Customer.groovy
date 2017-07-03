package com.guszak.paymentrestapi

class Customer {

	String name
	String email
    String cpf
    String phone
    String address
    String addressNumber
    String complement
    String province
    String postalCode
    String city
    Boolean deleted = false

    static constraints = {
        name blank: false
        email blank: false, email: true, unique: true
        cpf nullable: true, cpf: true, unique: true
        phone blank: false
        address nullable: true
        addressNumber nullable: true
        complement nullable: true
        province nullable: true
        postalCode nullable: true
        city nullable: true
    }

    static namedQueries = {
       noDeleted {
           eq 'deleted', false
       }

       search { searchString ->
            if (searchString&& searchString?.size() > 0) {
                or {
                    ilike("name", "%${searchString}%")
                    ilike("email", "%${searchString}%")
                }
            }
        }
    }
}
