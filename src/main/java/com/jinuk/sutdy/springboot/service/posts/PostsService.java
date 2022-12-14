package com.jinuk.sutdy.springboot.service.posts;

import com.jinuk.sutdy.springboot.domain.posts.Posts;
import com.jinuk.sutdy.springboot.domain.posts.PostsRepository;
import com.jinuk.sutdy.springboot.web.dto.PostsListResponseDto;
import com.jinuk.sutdy.springboot.web.dto.PostsResponseDto;
import com.jinuk.sutdy.springboot.web.dto.PostsSaveRequestDto;
import com.jinuk.sutdy.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById (Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id =" + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true) // readOnly를 하면 트랜잭션 범위는 유지하되, 조회 기능만 남겨두어 조회속도가 개선된다.
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList()); // Posts -> PostsListResponseDto -> List

    }

    @Transactional
    public void delete (Long id){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        postsRepository.delete(posts); // deleteById 메소드로 바로 삭제해도 되지만 존재하는 Posts인지 확인을 위해 조회 후 삭제한다.
    }
}
