package com.wish.biz.tp.metadata.base.tableddl;

import com.wish.biz.tp.common.common.UserContext;
import com.wish.biz.tp.common.model.dto.UserContextInfo;
import com.wish.biz.tp.metadata.constants.DatabaseConstant;
import com.wish.biz.tp.metadata.constants.MtpMetadataCommonErrCodeBussCheck;
import com.wish.biz.tp.metadata.enums.SQLOperationTypeEnum;
import com.wish.biz.tp.metadata.model.entity.TableDetail;
import com.wish.biz.tp.metadata.model.entity.Tp2002;
import com.wish.biz.tp.metadata.model.entity.Tp2003;
import com.wish.biz.tp.metadata.multi.bean.TableColumn;
import com.wish.biz.tp.metadata.multi.bean.TableIndex;
import com.wish.biz.tp.metadata.multi.bean.TableList;
import com.wish.biz.tp.metadata.multi.bean.TableListExtend;
import com.wish.biz.tp.metadata.utils.BeanTool;
import com.wish.plat.trans.exception.BizRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhaojian 2022-12-6
 */
@Component
@Slf4j
public class TableDdlForOracleV11 implements TableDdlStrategy {

    @Autowired
    private BaseTableDdl tableDdlBase;

    private static final String PROTOTYPE_PROJECT_ID = "00000";

    /**
     * 自增主键字段
     */
    private List<TableColumn> autoAddKeyColList = new ArrayList<>();
    private boolean proEnv;

    public TableDdlForOracleV11() {
        UserContextInfo userContextInfo = UserContext.get();
        if (userContextInfo != null) {
            if (PROTOTYPE_PROJECT_ID.equalsIgnoreCase(userContextInfo.getProjectId())){
                proEnv = true;
            }
        }
    }
    public String gainOperationType() {
        return SQLOperationTypeEnum.ORACLE11.getCode();
    }

    public String gainOperationType2(int a, String b) {
        return "";
    }
}
