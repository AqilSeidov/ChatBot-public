package HelperClasses;

import java.util.List;

public class ChatResponse {
    private List<Choice> choices;

    // Getters and setters
    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public static class Choice {
        private Message message;

        // Getters and setters
        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }

        public static class Message {
            private String content;

            // Getters and setters
            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
