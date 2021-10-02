package mashup.spring.seehyang.repository.user

import mashup.spring.seehyang.domain.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
}