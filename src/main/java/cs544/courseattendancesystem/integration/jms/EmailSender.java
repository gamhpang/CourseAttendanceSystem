package cs544.courseattendancesystem.integration.jms;

public interface EmailSender {
    public void sendReminderMessage(String to);

    public void sendConfirmationMessage(String to, String subject, String body);
}
