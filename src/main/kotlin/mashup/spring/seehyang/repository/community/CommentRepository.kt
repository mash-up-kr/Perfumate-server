package mashup.spring.seehyang.repository.community

import mashup.spring.seehyang.domain.entity.community.Comment
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository: JpaRepository<Comment, Long> {

    @Query("select c from Comment c " +
                   "join fetch c.story s " +
                   "join fetch c.user u " +
                   "join fetch c.parent p " +
           "where c.parent is null and s.id = :storyId")
    fun findFirstLevelCommentsByStoryId(@Param("storyId") storyId: Long, pageable: Pageable) : List<Comment>

    @Query("select c from Comment c " +
                   "join fetch c.story s " +
                   "join fetch c.user u " +
                   "join fetch c.parent p " +
           "where p.id = :commentId")
    fun findSecondLevelCommentsByCommentId(@Param("commentId") commentId: Long, pageable: Pageable) : List<Comment>


}