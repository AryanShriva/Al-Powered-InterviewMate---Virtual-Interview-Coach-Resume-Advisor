//package com.shriva.interview_mate_backend_java.model;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//
//import java.util.List;
//
//public class GeminiResponse {
//    @JsonProperty("candidates")
//    private List<Candidate> candidates;
//
//    public static class Candidate {
//        @JsonProperty("content")
//        private Content content;
//
//        public static class Content {
//            @JsonProperty("parts")
//            private List<Part> parts;
//
//            public static class Part {
//                @JsonProperty("text")
//                private String text;
//
//                public String getText() {
//                    return text;
//                }
//
//                public void setText(String text) {
//                    this.text = text;
//                }
//            }
//
//            public List<Part> getParts() {
//                return parts;
//            }
//
//            public void setParts(List<Part> parts) {
//                this.parts = parts;
//            }
//        }
//
//        public Content getContent() {
//            return content;
//        }
//
//        public void setContent(Content content) {
//            this.content = content;
//        }
//    }
//
//    public List<Candidate> getCandidates() {
//        return candidates;
//    }
//
//    public void setCandidates(List<Candidate> candidates) {
//        this.candidates = candidates;
//    }
//}