package com.board.movie.scrap.controller;

import com.board.movie.common.ApiResultDTO;
import com.board.movie.common.SuccessResponse;
import com.board.movie.scrap.dto.ScrapResponseDTO;
import com.board.movie.scrap.service.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/scraps")
public class ScrapController {

  private final ScrapService scrapService;

  // 스크랩 목록 조회
  @GetMapping
  public ApiResultDTO<List<ScrapResponseDTO>> listScraps() {
    return scrapService.listScraps();
  }

  // 스크랩 생성
  @PostMapping("/{postId}")
  public ApiResultDTO<SuccessResponse> createScrap(
      @PathVariable Long postId) {
    return scrapService.createScrap(postId);
  }

  // 스크랩 제거
  @DeleteMapping("/{postId")
  public ApiResultDTO<SuccessResponse> deleteScrap(
      @PathVariable Long postId) {
    return scrapService.deleteScrap(postId);
  }


}
