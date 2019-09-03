import {NgModule} from '@angular/core';
import {FlexLayoutModule} from '@angular/flex-layout';
import {CommonModule} from '@angular/common';

import {DataExplorerComponent} from './data-explorer.component';

import {CustomMaterialModule} from '../CustomMaterial/custom-material.module';
import {NguiDatetimePickerModule} from '@ngui/datetime-picker';
import {DatalakeRestService} from '../core-services/datalake/datalake-rest.service';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {CdkTableModule} from '@angular/cdk/table';
import {MatDatepickerModule, MatProgressSpinnerModule, MatSnackBarModule} from '@angular/material';
import {CoreUiModule} from '../core-iu/core-ui.module';
import {ExplorerComponent} from './explorer/explorer.component';
import {DataDownloadDialog} from './explorer/datadownloadDialog/dataDownload.dialog';


@NgModule({
    imports: [
        CommonModule,
        FlexLayoutModule,
        CustomMaterialModule,
        NguiDatetimePickerModule,
        ReactiveFormsModule,
        FormsModule,
        CdkTableModule,
        MatSnackBarModule,
        MatProgressSpinnerModule,
        CoreUiModule,
        MatDatepickerModule,
    ],
    declarations: [
        DataExplorerComponent,
        ExplorerComponent,
        DataDownloadDialog,
    ],
    providers: [
        DatalakeRestService
    ],
    entryComponents: [
        DataExplorerComponent,
    ],
    exports: [
    ]
})
export class DataExplorerModule {
}