package ru.tinkoff.fintech.service.notification

import java.lang.Exception

class CardNumberMaskerImpl: CardNumberMasker {

    override fun mask(cardNumber: String, maskChar: Char, start: Int, end: Int): String {
        val result = cardNumber.toMutableList()

        if(end-start<0)throw Exception("Start index cannot be greater than end index")
        result.forEachIndexed{ index, _ -> if(index in start until end) result[index]=maskChar}
        return result.toCharArray().joinToString("","","",16,"")
    }
}
