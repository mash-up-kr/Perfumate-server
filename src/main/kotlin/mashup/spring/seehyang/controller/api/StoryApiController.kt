package mashup.spring.seehyang.controller.api

import mashup.spring.seehyang.controller.api.dto.community.StoryCreateRequest
import mashup.spring.seehyang.controller.api.dto.community.StoryCreateResponse
import mashup.spring.seehyang.controller.api.dto.community.StoryDto
import mashup.spring.seehyang.controller.api.response.SeehyangResponse
import mashup.spring.seehyang.controller.api.response.SeehyangStatus
import mashup.spring.seehyang.domain.entity.user.User
import mashup.spring.seehyang.exception.UnauthorizedException
import mashup.spring.seehyang.service.StoryService
import org.springframework.web.bind.annotation.*

/**
 * Story Api Controller
 * C: 1. 향수 업로드  (Comment -> Comment API Controller)
 * R: 1. 스토리 id로 하나 가져오기 2. 향수 Id 로 여러개 가져오기
 * U: 1. 향수 좋아요.
 * D:
 */
@ApiV1
class StoryApiController(
    private val storyService: StoryService
) {

    /**
     * 1. id로 하나 가져오기
     */
    @GetMapping("/story/{id}")
    fun getStory(
        @PathVariable id : Long
    ): SeehyangResponse<StoryDto> {
        val story = storyService.getStoryDetail(id)
        return SeehyangResponse(StoryDto(story))
    }


    /**
     * 2. 향수 id 로 여러개 가져오기
     */
    @GetMapping("/perfume/{id}/story")
    fun getStoryByPerfume(
        @PathVariable(value = "id") perfumeId: Long,
        @RequestParam(value = "cursor") cursor: Long? = null
    ): SeehyangResponse<List<StoryDto>>{
        val stories = storyService.getStoriesByPerfume(perfumeId, cursor)
        val storyListDto = stories.map { StoryDto(it) }
        return SeehyangResponse(storyListDto)
    }

    @PostMapping("/story")
    fun createStory(
        @RequestBody createRequest: StoryCreateRequest,
        user: User,
    ) : SeehyangResponse<StoryCreateResponse> {
        if(user.isLogin().not())
            throw UnauthorizedException(SeehyangStatus.UNAUTHORIZED_USER)
        val story = storyService.create(user, createRequest)

        return SeehyangResponse(StoryCreateResponse(story))
    }



    @PostMapping("/story/{id}/like")
    fun likeStory(
        user: User,
        @PathVariable id : Long,
    ): SeehyangResponse<Map<String, Boolean>> {
        if(user.isLogin().not())
            throw UnauthorizedException(SeehyangStatus.UNAUTHORIZED_USER)
        val isLike = storyService.likeStory(user, id)

        return SeehyangResponse(mutableMapOf(Pair("isLike", isLike)))
    }

    @DeleteMapping("/story/{id}")
    fun deleteStory(
        user: User,
        @PathVariable id: Long
    ): SeehyangResponse<Map<String, Long>>{
        if(user.isLogin().not())
            throw UnauthorizedException(SeehyangStatus.UNAUTHORIZED_USER)
        val deletedId = storyService.deleteStory(user,id)

        return SeehyangResponse(mutableMapOf(Pair("id", deletedId)))
    }

}