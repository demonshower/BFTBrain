# BFTBrain学习系统特征常量定义
# 
# 功能说明：
# 定义机器学习模块使用的特征索引常量，
# 用于标识从BFT节点收集的不同类型的性能数据。
#
# 特征定义：
# - FAST_PATH_FREQUENCY: 快速路径使用频率，衡量协议优化路径的使用比例
# - SLOWNESS_OF_PROPOSAL: 提案处理延迟，反映网络和处理的延迟情况
# - REQUEST_SIZE: 客户端请求的数据大小，影响系统处理负载
# - HAS_FAST_PATH: 协议是否支持快速路径，二进制特征（0/1）
# - HAS_LEADER_ROTATION: 协议是否支持领导者轮换，二进制特征（0/1）
# - RECEIVED_MESSAGE_PER_SLOT: 每个时隙接收的消息数量，反映通信开销
# - REWARD: 强化学习的奖励信号，通常使用系统吞吐量作为奖励

REWARD = -1

FAST_PATH_FREQUENCY = 0
SLOWNESS_OF_PROPOSAL = 1
REQUEST_SIZE = 2
HAS_FAST_PATH = 3
HAS_LEADER_ROTATION = 4
RECEIVED_MESSAGE_PER_SLOT = 5