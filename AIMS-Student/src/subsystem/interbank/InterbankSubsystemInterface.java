package subsystem.interbank;

import entity.payment.PaymentCard;
import entity.payment.PaymentTransaction;

public interface InterbankSubsystemInterface{

    PaymentTransaction payOrder(PaymentCard card, int amount, String contents);

    PaymentTransaction refund(PaymentCard card, int amount, String contents);
}