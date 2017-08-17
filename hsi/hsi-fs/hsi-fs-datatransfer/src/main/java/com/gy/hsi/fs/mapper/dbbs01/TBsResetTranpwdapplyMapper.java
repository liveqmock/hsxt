package com.gy.hsi.fs.mapper.dbbs01;

import com.gy.hsi.fs.mapper.vo.dbbs01.TBsResetTranpwdapply;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsResetTranpwdapplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TBsResetTranpwdapplyMapper {
    int countByExample(TBsResetTranpwdapplyExample example);

    int deleteByExample(TBsResetTranpwdapplyExample example);

    int deleteByPrimaryKey(String applyId);

    int insert(TBsResetTranpwdapply record);

    int insertSelective(TBsResetTranpwdapply record);

    List<TBsResetTranpwdapply> selectByExample(TBsResetTranpwdapplyExample example);

    TBsResetTranpwdapply selectByPrimaryKey(String applyId);

    int updateByExampleSelective(@Param("record") TBsResetTranpwdapply record, @Param("example") TBsResetTranpwdapplyExample example);

    int updateByExample(@Param("record") TBsResetTranpwdapply record, @Param("example") TBsResetTranpwdapplyExample example);

    int updateByPrimaryKeySelective(TBsResetTranpwdapply record);

    int updateByPrimaryKey(TBsResetTranpwdapply record);
}