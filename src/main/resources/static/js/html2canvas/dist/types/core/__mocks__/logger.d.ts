export declare class Logger {
    static create(): void;

    static destroy(): void;

    static getInstance(): Logger;

    debug(): void;

    info(): void;

    error(): void;
}

export declare const logger: Logger;
