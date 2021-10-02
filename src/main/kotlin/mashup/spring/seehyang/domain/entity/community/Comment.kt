package mashup.spring.seehyang.domain.entity.community

import mashup.spring.seehyang.domain.entity.BaseTimeEntity
import mashup.spring.seehyang.domain.entity.user.User
import javax.persistence.*

@Entity
class Comment(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val contents: String,

    val numOfLike: Int,

    val numOfDislike: Int,

    /**
     * Comment Self-Join
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    val parent : Comment,

    @OneToMany(mappedBy = "parent")
    val children : MutableList<Comment> = mutableListOf(),

    /**
     * ======== Many to One ===========
     * Post
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "story_id")
    val story: Story,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user : User

) : BaseTimeEntity()