import {IPropertyListDescriptor} from '../IPropertyDescriptor';

export declare const enum DISPLAY {
    NONE = 0,
    BLOCK = 2,
    INLINE = 4,
    RUN_IN = 8,
    FLOW = 16,
    FLOW_ROOT = 32,
    TABLE = 64,
    FLEX = 128,
    GRID = 256,
    RUBY = 512,
    SUBGRID = 1024,
    LIST_ITEM = 2048,
    TABLE_ROW_GROUP = 4096,
    TABLE_HEADER_GROUP = 8192,
    TABLE_FOOTER_GROUP = 16384,
    TABLE_ROW = 32768,
    TABLE_CELL = 65536,
    TABLE_COLUMN_GROUP = 131072,
    TABLE_COLUMN = 262144,
    TABLE_CAPTION = 524288,
    RUBY_BASE = 1048576,
    RUBY_TEXT = 2097152,
    RUBY_BASE_CONTAINER = 4194304,
    RUBY_TEXT_CONTAINER = 8388608,
    CONTENTS = 16777216,
    INLINE_BLOCK = 33554432,
    INLINE_LIST_ITEM = 67108864,
    INLINE_TABLE = 134217728,
    INLINE_FLEX = 268435456,
    INLINE_GRID = 536870912
}

export declare type Display = number;
export declare const display: IPropertyListDescriptor<Display>;
