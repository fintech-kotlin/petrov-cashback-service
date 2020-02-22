package ru.tinkoff.fintech.service.notification

import java.lang.Exception

class CardNumberMaskerImpl: CardNumberMasker {

    override fun mask(cardNumber: String, maskChar: Char, start: Int, end: Int): String {
        var result:MutableList<Char> = cardNumber.toMutableList()

        if(end-start<0)throw Exception("Start index cannot be greater than end index")
        if(result.size>=end&&start>=0){
            for(i in start until end){
                result[i] = '#'
            }
        }
        else if(result.size>0) for (i in start..15) result[i] = '#'
        return result.toString().replace(", ","").replace("[","").replace("]","")
    }
}
