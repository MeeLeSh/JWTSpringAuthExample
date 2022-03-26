package com.example.chat.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "friends")
public class Friend {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "my_id")
    Long myId;

    @Column(name = "friend_id")
    Long friendId;

    public Friend(Long myId, Long friendId) {
        this.myId = myId;
        this.friendId = friendId;
    }
}
