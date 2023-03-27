## En
Manager your project git config file.

Use the same configuration for all items in the same folder.

This folder is called Project Space.

### Precondition

1. You need to use the same configuration of these items in the same folder.

2. There should be a '.gitConfig 'file in the same class as these projects. The structure and files are as follows

    - --ProjectSpace

        - --project01

        - --project02

        - --.gitConfig


### Use

1. Synchronize the project space configuration to the current project. When the project is opened, check whether it is consistent with the project space configuration. If it is inconsistent, prompt synchronization.
2. Synchronize all projects in the project space. Follow these steps:
    - Git --> GitConfig --> Sync Space Config



## Cn
管理你项目的git配置文件
同一个文件夹下的所有项目，使用同一份配置。
这个文件夹称为项目空间。

### 前置条件：

1.  你需要使用同一份配置的这些项目在同一个文件夹下。
2.  和这些项目同级应该有`.gitConfig`文件。结构和文件如下
    - --ProjectSpace
        - --project01
        - --project02
        - --.gitConfig


### 使用

1. 同步项目空间配置到当前项目。项目打开时自己检查是否和项目空间配置一致，不一致提示同步。
2. 同步项目空间下的所有项目。按照以下步骤：
    - Git --> GitConfig --> Sync Space Config