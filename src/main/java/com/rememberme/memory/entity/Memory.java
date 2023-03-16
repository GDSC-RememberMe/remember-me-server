package com.rememberme.memory.entity;

import com.rememberme.family.entity.Family;
import com.rememberme.memory.dto.MemoryRequestDto;
import lombok.*;

import javax.persistence.*;

@Table(name = "memory_quiz")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Memory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @JoinColumn(name ="family_id")
    private Family family;

    public void updateMemoryQuiz(MemoryRequestDto memoryRequestDto) {
        if (memoryRequestDto.getTitle() != null) {
            this.title = memoryRequestDto.getTitle();
        }
        if (memoryRequestDto.getContent() != null) {
            this.content = memoryRequestDto.getContent();
        }
        if (memoryRequestDto.getTagWho() != null) {
            this.tagWho = memoryRequestDto.getTagWho();
        }
        if (memoryRequestDto.getTagWhen() != null) {
            this.tagWhen = memoryRequestDto.getTagWhen();
        }
        if (memoryRequestDto.getTagWhere() != null) {
            this.tagWhere = memoryRequestDto.getTagWhere();
        }
        if (memoryRequestDto.getTagWhat() != null) {
            this.tagWhat = memoryRequestDto.getTagWhat();
        }
    }

    public void addImageFile(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void addAudioFile(String audioUrl) {
        this.audioUrl = audioUrl;
    }
}