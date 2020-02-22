package ru.tinkoff.fintech.service.notification

import ru.tinkoff.fintech.model.NotificationMessageInfo

class NotificationMessageGeneratorImpl(
    private val cardNumberMasker: CardNumberMasker
) : NotificationMessageGenerator {

    override fun generateMessage(notificationMessageInfo: NotificationMessageInfo): String {
        var result:StringBuilder=StringBuilder("Уважаемый, ")
        result.append(notificationMessageInfo.name +"!\n")
        result.append("Спешим Вам сообщить, что на карту "+cardNumberMasker.mask(notificationMessageInfo.cardNumber)+ "\n")
        result.append("начислен cashback в размере " + notificationMessageInfo.cashback + "\n")
        result.append("за категорию "+ notificationMessageInfo.category + ".\n")
        result.append("Спасибо за покупку "+notificationMessageInfo.transactionDate)
        return result.toString()
    }
}