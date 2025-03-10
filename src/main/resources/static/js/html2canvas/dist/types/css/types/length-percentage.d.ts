import {DimensionToken, NumberValueToken} from '../syntax/tokenizer';
import {CSSValue} from '../syntax/parser';

export declare type LengthPercentage = DimensionToken | NumberValueToken;
export declare type LengthPercentageTuple = [LengthPercentage] | [LengthPercentage, LengthPercentage];
export declare const isLengthPercentage: (token: CSSValue) => token is LengthPercentage;
export declare const parseLengthPercentageTuple: (tokens: LengthPercentage[]) => LengthPercentageTuple;
export declare const ZERO_LENGTH: NumberValueToken;
export declare const FIFTY_PERCENT: NumberValueToken;
export declare const HUNDRED_PERCENT: NumberValueToken;
export declare const getAbsoluteValueForTuple: (tuple: LengthPercentageTuple, width: number, height: number) => [number, number];
export declare const getAbsoluteValue: (token: LengthPercentage, parent: number) => number;
