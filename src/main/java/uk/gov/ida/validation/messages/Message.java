package uk.gov.ida.validation.messages;

/**
 * Definition of message which may be either a 'global' message or a 'field-related' message.  A global message is not
 * related to a field and is therefore a general message.
 */
public interface Message {
    /**
     * Get the field to which the message applies, if a field has been set.
     *
     * @return the name of field field, which may be null if this message is a global message not associated with any field.
     */
    String getField();

    /**
     * Get the message code, which is expected to be a canonical code for the domain.
     *
     * @return the message code.
     */
    String getCode();

    /**
     * Get the parameterised text which has been set for this message.
     *
     * @return the message text, which may contain parameter placeholders for interpolation.
     */
    String getParameterisedMessage();

    /**
     * Get the rendered text which has been set for this message. The message text is rendered in the default locale.
     *
     * @return the message text, which may contain parameter placeholders for interpolation.
     */
    String getRenderedMessage();

    /**
     * Get any message parameters that have been set for this message.  If set, these will be
     * interpolated in the message text in accordance with {@link java.text.MessageFormat} usage.
     *
     * @return the message parameters.
     */
    Object[] getMessageParameters();
}
