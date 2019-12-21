//
//  RegistrationInfoViewController.swift
//  TubeIOS
//
//  Created by Anshul Ahluwalia on 11/23/19.
//  Copyright © 2019 Anshul Ahluwalia. All rights reserved.
//

import UIKit
import FirebaseAuth

class RegistrationInfoViewController: UIViewController {
    @IBOutlet weak var nameTextField: UITextField!
    
    @IBOutlet weak var gtidTextField: UITextField!
    
    @IBOutlet weak var emailTextField: UITextField!
    
    @IBOutlet weak var majorTextField: UITextField!
    
    @IBOutlet weak var passwordTextField: UITextField!
    
    @IBOutlet weak var confirmPasswordTextField: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */
    override func shouldPerformSegue(withIdentifier identifier: String, sender: Any?) -> Bool {
        return false
    }
    @IBAction func registerSubmitButtonPressed(_ sender: UIButton) {
        if passwordTextField.text != confirmPasswordTextField.text {
            self.shouldPerformSegue(withIdentifier: "registrationToClasses", sender: self)
            print("Password text field does not equal confirm password text field")
            let alertController = UIAlertController(title: "Password Incorrect", message: "Please re-type password", preferredStyle: .alert)
            
            let defaultAction = UIAlertAction(title: "OK", style: .cancel, handler: nil)
            
            alertController.addAction(defaultAction)
            self.present(alertController, animated: true, completion: nil)
        }
        else {
            print("No error between password and confirm password")
            User.firstName = nameTextField.text!
            User.GTID = gtidTextField.text!
            User.email = emailTextField.text!
            User.major = emailTextField.text!
            User.password = passwordTextField.text!
            Auth.auth().createUser(withEmail: User.email, password: User.password) { (user, error) in
                
                if error == nil {
                    print("No error")
                    self.performSegue(withIdentifier: "registrationToClasses", sender: self)
                }
                else {
                    self.shouldPerformSegue(withIdentifier: "registrationToClasses", sender: self)
                    print("Error has occured")
                    let alertController = UIAlertController(title: "Error", message: error?.localizedDescription, preferredStyle: .alert)
                    let defaultAction = UIAlertAction(title: "OK", style: .cancel, handler: nil)
                    
                    alertController.addAction(defaultAction)
                    self.present(alertController, animated: true, completion: nil)
                }
            }
        }
        
        
    }
}
