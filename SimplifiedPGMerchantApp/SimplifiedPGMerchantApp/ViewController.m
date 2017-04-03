
#import "ViewController.h"

@interface ViewController ()

@end


@implementation ViewController

+(NSString*)generateOrderIDWithPrefix:(NSString *)prefix
{
    srand ( (unsigned)time(NULL) );
    int randomNo = rand(); //just randomizing the number
    NSString *orderID = [NSString stringWithFormat:@"%@%d", prefix, randomNo];
    return orderID;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(void)showController:(PGTransactionViewController *)controller
{
    if (self.navigationController != nil)
        [self.navigationController pushViewController:controller animated:YES];
    else
        [self presentViewController:controller animated:YES
                         completion:^{
                             
                         }];
}

-(void)removeController:(PGTransactionViewController *)controller
{
    if (self.navigationController != nil)
        [self.navigationController popViewControllerAnimated:YES];
    else
        [controller dismissViewControllerAnimated:YES
                                       completion:^{
                                       }];
}

-(IBAction)testPayment:(id)sender
{
    //Step 1: Create a default merchant config object
    PGMerchantConfiguration *mc = [PGMerchantConfiguration defaultConfiguration];
    
    //Step 2: Create the order with whatever params you want to add. But make sure that you include the merchant mandatory params
    NSMutableDictionary *orderDict = [NSMutableDictionary new];
    //Merchant configuration in the order object
    orderDict[@"MID"] = @"WorldP64425807474247";
    orderDict[@"ORDER_ID"] = @"TestMerchant000111008";
    orderDict[@"CUST_ID"] = @"mohit.aggarwal@paytm.com";
    orderDict[@"INDUSTRY_TYPE_ID"] = @"Retail";
    orderDict[@"CHANNEL_ID"] = @"WAP";
    orderDict[@"TXN_AMOUNT"] = @"1";
    orderDict[@"WEBSITE"] = @"worldpressplg";
    orderDict[@"CALLBACK_URL"] = @"https://pguat.paytm.com/paytmchecksum/paytmCheckSumVerify.jsp";
    orderDict[@"CHECKSUMHASH"] = @"o3ARWrsxEfuJwDhkG7/m57ZU+YpHJWNVOTqJb9kfp0fbioRG/lsn1ReNBPUr0UKMMB5Iq4e/JUVSHrbFl9g1VyCyQqcHl/jPOqNvYHVE4Ko=";
    
    PGOrder *order = [PGOrder orderWithParams:orderDict];
    
    //Step 3: Choose the PG server. In your production build dont call selectServerDialog. Just create a instance of the
    //PGTransactionViewController and set the serverType to eServerTypeProduction
    [PGServerEnvironment selectServerDialog:self.view completionHandler:^(ServerType eServerTypeProduction)
     {
         PGTransactionViewController *txnController = [[PGTransactionViewController alloc] initTransactionForOrder:order];
         
            //txnController.merchant = [PGMerchantConfiguration defaultConfiguration];
         
             txnController.serverType = eServerTypeProduction;
             txnController.merchant = mc;
             txnController.delegate = self;
             txnController.loggingEnabled = YES;
             [self showController:txnController];
        

             
         
     }];
}

#pragma mark PGTransactionViewController delegate

-(void)didFinishedResponse:(PGTransactionViewController *)controller response:(NSString *)responseString {
    DEBUGLOG(@"ViewController::didFinishedResponse:response = %@", responseString);
    NSString *title = [NSString stringWithFormat:@"Response"];
    [[[UIAlertView alloc] initWithTitle:title message:[responseString description] delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil] show];
    [self removeController:controller];
}

- (void)didCancelTransaction:(PGTransactionViewController *)controller error:(NSError*)error response:(NSDictionary *)response
{
    DEBUGLOG(@"ViewController::didCancelTransaction error = %@ response= %@", error, response);
    NSString *msg = nil;
    if (!error) msg = [NSString stringWithFormat:@"Successful"];
    else msg = [NSString stringWithFormat:@"UnSuccessful"];
    
    [[[UIAlertView alloc] initWithTitle:@"Transaction Cancel" message:msg delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil] show];
    [self removeController:controller];
}

- (void)didFinishCASTransaction:(PGTransactionViewController *)controller response:(NSDictionary *)response
{
    DEBUGLOG(@"ViewController::didFinishCASTransaction:response = %@", response);
}

@end
