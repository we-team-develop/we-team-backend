package weteam.backend.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import weteam.backend.common.domain.dto.VerifyResponse;
import weteam.backend.user.domain.dto.UserJoin;
import weteam.backend.user.domain.dto.UserLogin;
import weteam.backend.user.domain.dto.UserResponse;
import weteam.backend.user.mapper.UserMapper;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "user API")
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    @Operation(summary = "회원가입",
               description = "아이디, 닉네임 중복확인 후 입력된 데이터를 사용하여 회원가입을 한다",
               responses = {
                       @ApiResponse(responseCode = "200",
                                    content = @Content(schema = @Schema(implementation = UserResponse.class)))
               })
    public ResponseEntity<UserResponse> join(@RequestBody @Valid UserJoin request) {
        return ResponseEntity.ok(UserMapper.instance.toRes(userService.join(request)));
    }

    @PostMapping("/login")
    @Operation(summary = "로그인",
               description = "username, password를 사용한 일반 로그인",
               responses = {
                       @ApiResponse(responseCode = "200",
                                    content = @Content(schema = @Schema(implementation = UserResponse.class)))
               })
    public ResponseEntity<UserResponse> login(@RequestBody @Valid UserLogin request) {
        // TODO: 후에 jwt 로직으로 교체
        return ResponseEntity.ok(UserMapper.instance.toRes(userService.login(request)));
    }

    @Operation(summary = "아이디 중복 확인", responses = {
            @ApiResponse(responseCode = "200",
                         description = "중복 확인 성공",
                         content = @Content(schema = @Schema(implementation = VerifyResponse.class)))
    })
    @GetMapping("/verify/username/{username}")
    public ResponseEntity<VerifyResponse> verifyUsername(@PathVariable("username") String username) {

        return ResponseEntity.ok(userService.verifyUsername(username));
    }

    @GetMapping("/verify/nickname/{nickname}")
    @Operation(summary = "닉네임 중복 확인", responses = {
            @ApiResponse(responseCode = "200",
                         description = "중복 확인 성공",
                         content = @Content(schema = @Schema(implementation = VerifyResponse.class)))
    })
    public ResponseEntity<VerifyResponse> verifyNickname(@PathVariable("nickname") String nickname) {
        return ResponseEntity.ok(userService.verifyNickname(nickname));
    }
}
