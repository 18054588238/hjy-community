package com.personal.hjycommunitymodule.web.controller.common;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.personal.hjycommunitymodule.common.core.controller.BaseController;
import com.personal.hjycommunitymodule.common.core.domain.BaseResponse;
import com.personal.hjycommunitymodule.common.utils.ExcelUtils;
import com.personal.hjycommunitymodule.community.domain.HjyCommunity;
import com.personal.hjycommunitymodule.community.domain.dto.HjyCommunityDto;
import com.personal.hjycommunitymodule.community.domain.dto.HjyCommunityExcelDto;
import com.personal.hjycommunitymodule.community.service.HjyCommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName ExportExcelController
 * @Author liupanpan
 * @Date 2025/12/4
 * @Description
 */
@RestController
@RequestMapping("/exportExcel")
public class ExportExcelController extends BaseController {
    @Autowired
    private HjyCommunityService hjyCommunityService;
    @GetMapping("/exportCommunityExcel")
    public BaseResponse exportCommunityExcel(HttpServletResponse response, HjyCommunity hjyCommunity) {
        List<HjyCommunityDto> dtos = hjyCommunityService.queryList(hjyCommunity);

        List<HjyCommunityExcelDto> excelDtos = dtos.stream().map(dto -> {
            HjyCommunityExcelDto excelDto = HjyCommunityExcelDto.builder()
                    .communityId(dto.getCommunityId())
                    .communityName(dto.getCommunityName())
                    .communityCode(dto.getCommunityCode())
                    .communityDetailedAddress(dto.getCommunityDetailedAddress())
                    .communityProvinceName(dto.getCommunityProvinceName())
                    .communityCityName(dto.getCommunityCityName())
                    .communityTownName(dto.getCommunityTownName())
                    .remark(dto.getRemark())
                    .createTime(dto.getCreateTime())
                    .build();
            return excelDto;
        }).collect(Collectors.toList());

        ExcelUtils.exportExcel(response,HjyCommunityExcelDto.class,excelDtos,"小区信息",new ExportParams("小区信息列表","小区信息"));
        return BaseResponse.success("文件导出成功");
    }
}
