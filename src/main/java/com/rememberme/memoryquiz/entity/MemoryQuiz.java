package com.rememberme.memoryquiz.entity;

import com.rememberme.memoryquiz.dto.MemoryQuizRequestDto;
import com.rememberme.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Table(name = "memory_quiz")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class MemoryQuiz {

    @Id
    @GeneratedValue
    @Column(name = "memory_quiz_id")
    private Long id;

    private String title;

    private String content;

    @Column(name = "tag_who")
    private String tagWho;

    @Column(name = "tag_when")
    private String tagWhen;

    @Column(name = "tag_where")
    private String tagWhere;

    @Column(name = "tag_what")
    private String tagWhat;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "audio_url")
    private String audioUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id")
    private User user;

    public void updateMemoryQuiz(MemoryQuizRequestDto memoryQuizRequestDto) {
        if (memoryQuizRequestDto.getTitle() != null) {
            this.title = memoryQuizRequestDto.getTitle();
        }
        if (memoryQuizRequestDto.getContent() != null) {
            this.content = memoryQuizRequestDto.getContent();
        }
        if (memoryQuizRequestDto.getTagWho() != null) {
            this.tagWho = memoryQuizRequestDto.getTagWho();
        }
        if (memoryQuizRequestDto.getTagWhen() != null) {
            this.tagWhen = memoryQuizRequestDto.getTagWhen();
        }
        if (memoryQuizRequestDto.getTagWhere() != null) {
            this.tagWhere = memoryQuizRequestDto.getTagWhere();
        }
        if (memoryQuizRequestDto.getTagWhat() != null) {
            this.tagWhat = memoryQuizRequestDto.getTagWhat();
        }
    }
}