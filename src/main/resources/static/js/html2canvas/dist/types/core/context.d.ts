import {Logger} from './logger';
import {Cache, ResourceOptions} from './cache-storage';
import {Bounds} from '../css/layout/bounds';

export declare type ContextOptions = {
    logging: boolean;
    cache?: Cache;
} & ResourceOptions;

export declare class Context {
    private static instanceCount;
    windowBounds: Bounds;
    readonly logger: Logger;
    readonly cache: Cache;
    private readonly instanceName;

    constructor(options: ContextOptions, windowBounds: Bounds);
}
