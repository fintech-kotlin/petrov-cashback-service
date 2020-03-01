package ru.tinkoff.fintech.service.cashback

import ru.tinkoff.fintech.model.TransactionInfo
import java.text.DecimalFormat
import java.time.LocalDate

internal const val LOYALTY_PROGRAM_BLACK = "BLACK"
internal const val LOYALTY_PROGRAM_ALL = "ALL"
internal const val LOYALTY_PROGRAM_BEER = "BEER"
internal const val MAX_CASH_BACK = 3000.0
internal const val MCC_SOFTWARE = 5734
internal const val MCC_BEER = 5921

class CashbackCalculatorImpl : CashbackCalculator {

    override fun calculateCashback(transactionInfo: TransactionInfo): Double {
        val df=DecimalFormat("####.#")
        var result:Double=0.0
        val date:LocalDate
        if(transactionInfo.loyaltyProgramName.equals(LOYALTY_PROGRAM_BLACK)){
            result=transactionInfo.transactionSum*0.01
        }
        if(transactionInfo.transactionSum.rem(666).toInt() == 0)result+=6.66
        if(transactionInfo.loyaltyProgramName.equals(LOYALTY_PROGRAM_ALL)&&transactionInfo.mccCode== MCC_SOFTWARE&&isItPalyndrome(transactionInfo.transactionSum)){
           result= calculateNOK(transactionInfo.firstName, transactionInfo.lastName)/1000.0 *transactionInfo.transactionSum/100
        }
        if(transactionInfo.loyaltyProgramName.equals(LOYALTY_PROGRAM_BEER)&&transactionInfo.mccCode== MCC_BEER){
            if(transactionInfo.firstName.toLowerCase()=="олег"&&transactionInfo.lastName.toLowerCase()=="олегов"){
                result=transactionInfo.transactionSum*0.1
            }
            else if(transactionInfo.firstName.toLowerCase()=="олег")  result=transactionInfo.transactionSum*0.07
            else if(monthAndLetterVerifier(LocalDate.now().monthValue,transactionInfo.firstName.toLowerCase()[0])) result=transactionInfo.transactionSum*0.05
            else if(monthAndLetterVerifier(LocalDate.now().monthValue-1,transactionInfo.firstName.toLowerCase()[0])||monthAndLetterVerifier(LocalDate.now().monthValue+1,transactionInfo.firstName.toLowerCase()[0])){
                result=transactionInfo.transactionSum*0.03
            }
            else result=transactionInfo.transactionSum*0.02
        }
        result= String.format("%.2f",result).toDouble()
        val maxCashback=3000.0-transactionInfo.cashbackTotalValue
        if(maxCashback>result)return result
        else return maxCashback
    }

    fun isItPalyndrome(transationsum: Double):Boolean{
        val temp=transationsum.toString().replace(".", "").toCharArray()
        var differences=0
        for(i in 0..temp.size/2){
            if(temp[i]!=temp[temp.size-1-i]) {
                differences++
            }
        }
        if (differences>1) return false
        return true
    }
    fun calculateNOK(name:String, surname:String):Int{
        val nameLenght=name.length
        val surnameLength=surname.length
        var result:Int
        if(nameLenght>surnameLength) result=nameLenght else result=surnameLength
        for(i in 0..1000000){
            if(result%nameLenght==0&&result%surnameLength==0) break
            else result+=1
        }
        return result
    }
    fun monthAndLetterVerifier(month: Int, char: Char):Boolean{
        if(((month==3)||(month==5))&&char=='м') return true
        if(month==2&&char=='ф') return true
        if(month==1&&char=='я') return true
        if(((month==4||month==8))&&char=='а')return true
        if(((month==6||month==7))&&char=='и')return true
        if(month==9&&char=='с') return true
        if(month==10&&char=='о') return true
        if(month==11&&char=='н') return true
        if(month==12&&char=='д') return true
        return false
    }
}