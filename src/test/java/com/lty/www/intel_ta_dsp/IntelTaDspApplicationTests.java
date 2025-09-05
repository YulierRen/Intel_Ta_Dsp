package com.lty.www.intel_ta_dsp;

import com.lty.www.intel_ta_dsp.dto.UserDaynoteDTO;
import com.lty.www.intel_ta_dsp.dto.aidto.AiDiaryDTO;
import com.lty.www.intel_ta_dsp.dto.aidto.ScheduleGenerateDTO;
import com.lty.www.intel_ta_dsp.entity.*;
import com.lty.www.intel_ta_dsp.mapper.*;
import com.lty.www.intel_ta_dsp.service.FriendRequestService;
import com.lty.www.intel_ta_dsp.service.ScheduleService;
import com.lty.www.intel_ta_dsp.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static com.lty.www.intel_ta_dsp.entity.UserProfile.Gender.OTHER;

@SpringBootTest
class IntelTaDspApplicationTests {
    @Test
    void contextLoads() {
    }

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;


    @Test
    void addUser(){
//        userService.addUser(User.builder()
//                .username("alice")
//                .password("123456")
//                .role("USER")
//                .build());
//
//        userService.addUser(User.builder()
//                .username("bob")
//                .password("123456")
//                .role("USER")
//                .build());
//
//        userService.addUser(User.builder()
//                .username("admin")
//                .password("admin123")
//                .role("ADMIN")
//                .build());
          userService.addUser(User.builder()
                .username("刘天野")
                .password("123")
                .role("ADMIN")
                .build());

    }
    @Autowired
    UserProfileMapper userProfileMapper;

    @Test
    void addProfile() {
        UserProfile newUserProfile = UserProfile.builder()
                .userId(26L)
                .nickname("你的昵称，可更改")
                .gender(OTHER)
                .avatarUrl("待上传")
                .birthday(null)
                .bio("这个人很懒，懒得介绍自己")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        userProfileMapper.insertUserProfile(newUserProfile);
    }

    @Autowired
    private ScheduleService scheduleService;

    @Test
    void testFindFromStartToEnd() {
        ScheduleGenerateDTO dto = new ScheduleGenerateDTO();
        dto.setUserId(26L);
//        dto.setStartTime("2025-12-16 00:00:00");
//        dto.setEndTime("2025-12-21 23:59:59");

        List<Schedule> schedules = scheduleService.findFromStartToEnd(dto);

        System.out.println("查询到的日程数量：" + schedules.size());
        for (Schedule s : schedules) {
            System.out.println(s);
        }

    }


    @Test
    void testUserMapper() {
        System.out.println(userMapper.findByUsername("user"));
    }

    @Test
    void testScheduleMapper() {
        System.out.println(scheduleService.getScheduleByStudentId(5L));
    }


    @Autowired
    private UserFriendMapper userFriendMapper;
    @Test
    void testUserFriendMapper() {
        UserFriend userFriend = new UserFriend();
        userFriend.setUserId(5L);
        userFriend.setFriendId(6L);
        userFriendMapper.addFriend(userFriend);
    }
    @Test
    void testUserMapperFriendList(){
        System.out.println(userFriendMapper.getFriendList(5L));
        System.out.println(userFriendMapper.getFriendList(6L));
    }

    @Autowired
    private FriendRequestService friendRequestService;
    @Test
    void testFriendRequestMapper() {
        FriendRequest friendRequest = FriendRequest.builder()
                .senderId(5L)
                .receiverId(6L)
                .build();
        friendRequestService.sendAddFriendRequest(friendRequest);
    }

    @Autowired
    private UserDiaryMapper userDiaryMapper;

    @Test
    void testUserDiaryMapper() {
        System.out.println(userDiaryMapper.findAll());
    }



    @Autowired
    private UserDaynoteMapper userDaynoteMapper;

    private static final Long TEST_USER_ID = 26L;
    private static final LocalDate TEST_DATE = LocalDate.now(); // 只保留日期，无时间

    @Test
    void testUserDaynoteMapper() {
        LocalDateTime ldtStart = LocalDateTime.of(2025, 7, 27, 0, 0, 0);
        LocalDateTime ldtEnd = LocalDateTime.of(2025, 7, 29, 0, 0, 0);

        AiDiaryDTO dto = new AiDiaryDTO();
        dto.setUserId(TEST_USER_ID);
        dto.setStartTime(Date.from(ldtStart.atZone(ZoneId.systemDefault()).toInstant()));
        dto.setEndTime(Date.from(ldtEnd.atZone(ZoneId.systemDefault()).toInstant()));
        System.out.println(userDaynoteMapper.findFromStartToEnd(dto));
    }

    @Test
    void testCrud() {
        System.out.println("开始测试 UserDaynote CRUD, 日期: " + TEST_DATE);

        // 构造 DTO 查询条件
        UserDaynoteDTO dto = UserDaynoteDTO.builder()
                .userId(TEST_USER_ID)
                .date(TEST_DATE)
                .build();

        // 1. 初始查询
        List<UserDaynote> beforeInsert = userDaynoteMapper.selectBy(dto);
        System.out.println("【初始查询】结果: " + beforeInsert);

        // 2. 插入数据
        UserDaynote note = UserDaynote.builder()
                .userId(TEST_USER_ID)
                .title("测试日记标题")
                .description("这是一个测试日记内容")
                .date(TEST_DATE)
                .build();

        boolean inserted = userDaynoteMapper.insert(note);
        System.out.println("【插入操作】成功？" + inserted);

        // 3. 插入后查询
        List<UserDaynote> afterInsert = userDaynoteMapper.selectBy(dto);
        System.out.println("【插入后查询】结果: " + afterInsert);

        // 4. 修改
        if (!afterInsert.isEmpty()) {
            UserDaynote toUpdate = afterInsert.get(0);
            toUpdate.setTitle("修改后的标题");
            toUpdate.setDescription("这是修改后的内容");

            boolean updated = userDaynoteMapper.update(toUpdate);
            System.out.println("【更新操作】成功？" + updated);
        }

        // 5. 更新后查询
        List<UserDaynote> afterUpdate = userDaynoteMapper.selectBy(dto);
        System.out.println("【更新后查询】结果: " + afterUpdate);

        // 6. 删除
        boolean deleted = userDaynoteMapper.delete(dto);
        System.out.println("【删除操作】成功？" + deleted);

        // 7. 删除后查询
        List<UserDaynote> afterDelete = userDaynoteMapper.selectBy(dto);
        System.out.println("【删除后查询】结果: " + afterDelete);
    }


    @Test
    void show(){

        System.out.println(TEST_USER_ID);
        System.out.println(TEST_DATE);
        UserDaynoteDTO dto = UserDaynoteDTO.builder()
                .userId(TEST_USER_ID)
                .date(TEST_DATE)
                .build();
        List<UserDaynote> afterInsert = userDaynoteMapper.selectBy(dto);
        System.out.println("【插入后查询】结果: " + afterInsert);
    }


    @Test
    void testconn(){
        String url = "jdbc:mysql://47.105.37.144:3306/Intel_Ta_Dsp?useSSL=false&serverTimezone=UTC";
        String username = "root";
        String password = "root123";
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            System.out.println("连接成功");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
