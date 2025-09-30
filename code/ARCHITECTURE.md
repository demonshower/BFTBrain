# BFTBrain 系统架构说明

## 概述
BFTBrain是一个基于机器学习的自适应拜占庭容错(BFT)系统框架。它能够根据系统运行时的性能特征动态选择最优的BFT协议，实现协议的自适应切换。

## 系统架构

### 主要代码目录结构

```
code/
├── src/com/gbft/framework/          # Java框架核心代码
│   ├── core/                        # 核心实体类
│   ├── coordination/                # 分布式协调组件
│   ├── plugins/                     # 插件系统
│   ├── fault/                       # 故障注入系统
│   ├── data/                        # 数据模型和协议缓冲区
│   ├── utils/                       # 工具类和配置管理
│   └── statemachine/               # 状态机实现
├── learning/                        # Python机器学习模块
└── benchmarks/                      # 基准测试结果目录
```

### 核心组件

#### 1. 框架核心 (code/src/com/gbft/framework/core/)

- **Entity.java**: 所有分布式实体的抽象基类
  - 协议状态管理
  - 消息处理和路由
  - 插件系统集成
  - 故障注入支持

- **Node.java**: BFT节点实现
  - 请求执行和状态维护
  - 检查点管理
  - 性能特征收集
  - 学习代理通信

- **Client.java**: 客户端实现
  - 请求生成和负载控制
  - 开环/闭环模式支持
  - 基准测试负载生成

#### 2. 协调系统 (code/src/com/gbft/framework/coordination/)

- **CoordinatorServer.java**: 实验控制中心
  - 管理实验生命周期
  - 配置分发
  - 基准测试控制
  - 性能监控

- **CoordinatorUnit.java**: 协调单元
  - 节点间通信管理
  - 事件处理和同步

#### 3. 插件系统 (code/src/com/gbft/framework/plugins/)

- **PluginManager.java**: 插件管理器
  - 动态插件加载
  - 插件组合配置
  - 运行时插件切换

- **插件类型**:
  - RolePlugin: 节点角色管理
  - MessagePlugin: 消息处理
  - PipelinePlugin: 消息流水线
  - TransitionPlugin: 状态转换

#### 4. 故障注入系统 (code/src/com/gbft/framework/fault/)

- **Fault.java**: 故障注入基类
- **具体故障类型**:
  - InDarkFault: 节点失联模拟
  - PollutionFault: 数据污染攻击
  - SlowProposalFault: 提案延迟
  - TimeoutFault: 超时故障

#### 5. 机器学习模块 (code/learning/)

- **learning_agent.py**: 学习代理主程序
  - 特征数据接收
  - 机器学习模型管理
  - 协议选择决策
  - 在线学习更新

- **支持的学习模型**:
  - QuadraticRF: 二次随机森林模型
  - MultiRF: 多模型随机森林
  - SingleRF: 单一随机森林
  - ADAPT/ADAPT+: 适应性模型
  - Heuristic: 启发式方法

- **constants.py**: 特征常量定义
  - 性能特征索引
  - 协议编码特征
  - 奖励信号定义

### 数据流和通信

#### 1. 系统初始化流程
1. CoordinatorServer启动并等待节点连接
2. 分发协议配置到所有节点
3. 初始化插件系统和建立连接
4. 启动基准测试和学习代理

#### 2. 运行时学习循环
1. 节点收集性能特征
2. 定期向学习代理报告特征数据
3. 学习代理基于历史数据训练模型
4. 预测各协议性能并选择最优协议
5. 将协议切换决策发送回节点
6. 节点执行协议切换

#### 3. 特征数据流
- 节点 → 学习代理: 性能特征报告
- 学习代理 → 节点: 协议选择决策
- 所有节点 → 协调服务器: 基准测试数据

### 配置系统

配置文件位于 `config/` 目录:
- `config.framework.yaml`: 框架基础配置
- `config.{protocol}.yaml`: 各BFT协议的具体配置
- 支持动态配置更新和协议切换

### 实验系统

实验配置位于 `exp/` 目录:
- `static/`: 静态协议实验
- `dynamic/`: 动态协议切换实验
- `pollution/`: 污染攻击实验
- 支持多种实验场景和负载模式

## 设计特点

1. **模块化架构**: 基于插件的设计支持灵活的协议组合
2. **可扩展性**: 易于添加新的BFT协议和学习算法
3. **故障容错**: 内置故障注入和恢复机制
4. **性能监控**: 实时性能指标收集和分析
5. **自适应性**: 基于机器学习的动态协议选择

## 支持的BFT协议

- PBFT
- Zyzzyva
- CheapBFT
- SBFT
- HotStuff
- Prime
- 可通过插件系统扩展更多协议

该架构设计使BFTBrain能够在各种网络条件和负载模式下自动选择最优的BFT协议，实现高性能和高可用性的分布式系统。

