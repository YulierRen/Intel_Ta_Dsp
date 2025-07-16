package com.lty.www.intel_ta_dsp.mapper;

import com.lty.www.intel_ta_dsp.dto.UserDaynoteDTO;
import com.lty.www.intel_ta_dsp.dto.aidto.AiDiaryDTO;
import com.lty.www.intel_ta_dsp.entity.UserDaynote;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface UserDaynoteMapper {
    List<UserDaynote> selectBy(UserDaynoteDTO dto);

    boolean insert(UserDaynote userDaynote);

    boolean update(UserDaynote userDaynote);

    boolean delete(UserDaynoteDTO dto);

    List<UserDaynote> findFromStartToEnd(AiDiaryDTO dto);
}
