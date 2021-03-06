package com.guszak.paymentrestapi

import static org.springframework.http.HttpStatus.*

class ErrorController {

    def index() {

        def exception = request.exception.cause
        def message = ExceptionMapper.mapException(exception)
        def status = message.status

        response.status = status
            render(view: "/error", model: [status: status, exception: exception])
    }
}