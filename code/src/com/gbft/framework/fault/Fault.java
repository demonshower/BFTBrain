package com.gbft.framework.fault;

/**
 * Fault抽象类 - 故障注入系统基类
 * 
 * 功能说明：
 * 这个抽象类定义了BFT系统中故障注入机制的基础框架。
 * 用于模拟分布式环境中可能出现的各种故障场景。
 * 
 * 核心功能：
 * 1. 故障策略管理：定义故障的触发条件和行为模式
 * 2. 动态控制：支持运行时开启、关闭和切换故障
 * 3. 协议感知：根据当前使用的BFT协议调整故障行为
 * 4. 实体选择：精确控制哪些节点受到故障影响
 * 
 * 具体故障类型（由子类实现）：
 * - InDarkFault: 模拟节点网络分区或失联
 * - PollutionFault: 模拟恶意节点的数据污染攻击
 * - SlowProposalFault: 模拟提案处理延迟
 * - TimeoutFault: 模拟网络超时和处理超时
 * 
 * 配置机制：通过配置文件控制故障的启用状态和参数
 */

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.gbft.framework.utils.Config;

public abstract class Fault {

    protected String policyName;

    protected AtomicBoolean isOverridden;

    protected String getField(String fieldName) {
        return "fault." + policyName + "." + fieldName;
    }

    public Fault(String policyName) {
        this.policyName = policyName;

        // check if is overridden
        this.isOverridden = new AtomicBoolean(false);
        var overridden_list = Config.stringList("protocol.fault-override");
        if (overridden_list != null) {
            if (overridden_list.contains(policyName)) {
                this.isOverridden.set(true);
            }
        }
    }

    public void reloadProtocol(String protocol) {
        Config.setCurrentProtocol(protocol);
        var overridden_list = Config.stringList("protocol.fault-override");
        if (overridden_list != null) {
            if (overridden_list.contains(policyName)) {
                this.isOverridden.set(true);
                return;
            }
        }
        this.isOverridden.set(false);
    }

    public List<Integer> getAffectedEntities() {
        return Config.intList(getField("affected-entities"));
    }
}
